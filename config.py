import logger
import udata.database
import search.searcher

SERVER_IP = "127.0.0.1"
SERVER_PORT = 7860

ALL_ONLINE_USERS = []

ITEMS_IMG_ROOT = "C:\\Users\\Administrator\\Desktop\\Uapp\\itemimgs\\"

LOG_SAVE_PATH = "server.log"

LOGGER = logger.UappLogger(LOG_SAVE_PATH)

DBSERVER = udata.database.DB(LOGGER)

SEARCHER = search.searcher.Searcher()


