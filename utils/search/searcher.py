import os
import pickle
import jieba
from zhon.hanzi import punctuation as hanzi_punc
from string import punctuation as english_punc
import csv

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

    def get_related_post_ids(self, searchText, is_for_lost, enable_vague=False):
        lookup = self.lost_lookup if is_for_lost else self.found_lookup 
        words = []
        if searchText.strip():
            words = jieba.cut_for_search(searchText.strip())
            words = [' ' if i.strip() in self.punctuation else i.strip() for i in words]
            words = [i.strip() for i in words if i.strip()]
        word_ids = []
        if enable_vague:
            for token in self.token2id:
                for word in words:
                    if word in token:
                        word_ids.append(self.token2id[token])
        else:
            for word in words:
                if word in self.token2id:
                    word_ids.append(self.token2id[word])
        res = {}
        for wid in word_ids:
            for i in lookup[wid]:
                if i not in res:
                    res[i] = 1
                else:
                    res[i] += 1
        sorted_res = dict(sorted(res.items(), key = lambda x: x[1], reverse=True))
        
        return list(sorted_res.keys())

    def expand_dictionary_by_words(self, tokens_list):
        id = len(self.id2token)
        for token in tokens_list:
            token = token.strip()
            if token and token not in self.token2id:
                self.token2id[token] = id
                self.id2token[id] = token
                self.lost_lookup[id] = []
                self.found_lookup[id] = []
                id = id + 1
    
    def expand_dictionary_by_file(self, words_list_file):
        id = len(self.id2token)
        with open(words_list_file, "r", encoding='utf-8') as f:
            for line in f.readlines():
                if line.strip('\n').strip():
                    token, *rest = line.split()
                    token = token.strip()
                    if token and token not in self.token2id:
                        self.token2id[token] = id
                        self.id2token[id] = token
                        self.lost_lookup[id] = []
                        self.found_lookup[id] = []
                        id = id + 1

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
        self.save_table()
        self.update_config()


if __name__ == '__main__':
    csv_data_path = r"/home/vhicr/Desktop/Uapp/utils/search/words/csv"
    txt_data_path = r"/home/vhicr/Desktop/Uapp/utils/search/words/txt"
    csv_files = os.listdir(csv_data_path)
    txt_files = os.listdir(txt_data_path)
    id2token = {}
    token2id = {}
    lost_lookup = {}
    found_lookup = {}
    id = 0
    for file in csv_files:
        with open(os.path.join(csv_data_path, file), "r", encoding='utf-8') as csvfile:
            reader = csv.reader(csvfile, delimiter=',', quotechar='"')
            next(reader)
            for row in reader:
                token = row[0].strip()
                if token and token not in token2id:
                    token2id[token] = id
                    id2token[id] = token
                    lost_lookup[id] = []
                    found_lookup[id] = []
                    id = id + 1  
                    
    for file in txt_files:
        with open(os.path.join(txt_data_path, file), "r", encoding='utf-8') as f:
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
    # searcher = Searcher()
    # searcher.expand_dictionary_by_file('/home/vhicr/Desktop/Uapp/utils/search/words/txt/123.txt')
    # searcher.save_table()
    # searcher.update_config()
    # print(searcher.lost_lookup[searcher.token2id['山地车']])


