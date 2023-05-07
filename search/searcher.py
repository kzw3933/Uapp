import os
import pickle
import jieba
from zhon.hanzi import punctuation as hanzi_punc
from string import punctuation as english_punc

class Searcher:
    def __init__(self, table_file=r"C:\Users\Administrator\Desktop\Uapp\search\words_table.pkl"):
        self.table_file = table_file
        self.punctuation = hanzi_punc + english_punc
        self.id2token, self.token2id, self.lost_lookup, self.found_lookup = self.load_table(table_file)
    def load_table(self, table_file):
        with open(table_file, 'rb') as f:
            id2token, token2id, lost_lookup, found_lookup = pickle.load(f)

        return id2token, token2id, lost_lookup, found_lookup

    def save_table(self):
        table = (self.id2token, self.token2id, self.lost_lookup, self.found_lookup)
        with open(self.table_file, 'wb') as f:
            pickle.dump(table, f)

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
        
            
    def update(self, post_id, item_type, item_position, item_desc, is_for_lost):
        split_words = []
        for text in (item_type, item_position, item_desc):
            if text.strip():
                words = jieba.cut_for_search(text.strip())
                words = [' ' if i.strip() in self.punctuation else i.strip() for i in words]
                words = [i.strip() for i in words if i.strip()]
                split_words.extend(words)
        word_ids = []
        for word in words:
            if word in self.token2id:
                word_ids.append(self.token2id[word])
                
        lookup = self.lost_lookup if is_for_lost else self.found_lookup 

        for id in word_ids:
            if post_id not in lookup[id]:
                lookup[id].append(post_id)

        self.save_table()


if __name__ == '__main__':
    data_path = r"C:\Users\Administrator\Desktop\Uapp\search\data"
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
    with open(r"C:\Users\Administrator\Desktop\Uapp\search\words_table.pkl", "wb") as f:
        pickle.dump(table, f)
    searcher = Searcher()
    print(searcher.get_related_post_ids("山地车"))


