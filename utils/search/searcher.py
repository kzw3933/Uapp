import os
import pickle
import jieba
from zhon.hanzi import punctuation as hanzi_punc
from string import punctuation as english_punc

class Searcher:
    def __init__(self):
        self.config_file = r"/home/vhicr/Desktop/Uapp/utils/search/config.txt"
        self.table_file_1, self.table_file_2 = self.load_config()
        self.punctuation = hanzi_punc + english_punc
        self.id2token, self.token2id, self.lost_lookup, self.found_lookup = self.load_table(self.table_file_1)
    def load_table(self, table_file):
        with open(table_file, 'rb') as f:
            id2token, token2id, lost_lookup, found_lookup = pickle.load(f)

        return id2token, token2id, lost_lookup, found_lookup

    def save_table(self):
        table = (self.id2token, self.token2id, self.lost_lookup, self.found_lookup)
        with open(self.table_file_2, 'wb') as f:
            pickle.dump(table, f)

    def load_config(self):
        ret = []
        with open(self.config_file, "r") as f:
            for line in f.readlines():
                ret.append(line.strip())

        return ret

    def get_related_post_ids(self, searchText, is_for_lost):
        lookup = self.lost_lookup if is_for_lost else self.found_lookup 
        words = []
        if searchText.strip():
            words = jieba.cut_for_search(searchText.strip())
            words = [' ' if i.strip() in self.punctuation else i.strip() for i in words]
            words = [i.strip() for i in words if i.strip()]
        word_ids = []
        for word in words:
            if word in self.token2id:
                word_ids.append(self.token2id[word])
        res = {}
        for id in word_ids:
            for i in lookup[id]:
                if i not in res:
                    res[i] = 1
                else:
                    res[i] += 1
        sorted_res = dict(sorted(res.items(), key = lambda x: x[1], reverse = True))
        
        return list(sorted_res.keys())

    def update_config(self):
        with open(self.config_file, "w") as f:
            f.write(self.table_file_2+"\n")
            f.write(self.table_file_1)
            
    def update(self, post_id, item_type, item_position, item_desc, is_for_lost):
        split_words = []
        for text in (item_type, item_position, item_desc):
            if text.strip():
                words = jieba.cut_for_search(text.strip())
                words = [' ' if i.strip() in self.punctuation else i.strip() for i in words]
                words = [i.strip() for i in words if i.strip()]
                split_words.extend(words)
        word_ids = []
        for word in split_words:
            if word in self.token2id:
                word_ids.append(self.token2id[word])
                
        lookup = self.lost_lookup if is_for_lost else self.found_lookup 

        for id in word_ids:
            if post_id not in lookup[id]:
                lookup[id].append(post_id)
        # print(self.lost_lookup[self.token2id['山地车']])
        self.save_table()
        self.update_config()


if __name__ == '__main__':
    data_path = r"/home/vhicr/Desktop/Uapp/utils/search/words"
    files = os.listdir(data_path)
    id2token = {}
    token2id = {}
    lost_lookup = {}
    found_lookup = {}
    id = 0
    for file in files:
        with open(os.path.join(data_path, file), "r", encoding='utf-8') as f:
            for line in f.readlines():
                if line.strip('\n').strip():
                    token, *rest = line.split()
                    token = token.strip()
                    if token and token not in token2id:
                        token2id[token] = id
                        id2token[id] = token
                        lost_lookup[id] = []
                        found_lookup[id] = []
                        id = id + 1
    
    table = (id2token, token2id, lost_lookup, found_lookup)
    with open(r"/home/vhicr/Desktop/Uapp/utils/search/words_table_a.pkl", "wb") as f:
        pickle.dump(table, f)
    searcher = Searcher()
    print(searcher.lost_lookup[searcher.token2id['山地车']])


