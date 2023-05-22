import utils.log.logger as logger
import utils.data.database
import utils.search.searcher

SERVER_IP = "210.45.112.121"
SERVER_PORT = 7860

ITEMS_FULL_IMG_ROOT = "/home/vhicr/Desktop/Uapp/imgs/items/full/"
ITEMS_THUMB_IMG_ROOT = "/home/vhicr/Desktop/Uapp/imgs/items/thumb/"

AVATAR_IMG_ROOT = "/home/vhicr/Desktop/Uapp/imgs/avatars/"

LOG_SAVE_PATH = "/home/vhicr/Desktop/Uapp/logs/server.log"

LOGGER = logger.UappLogger(LOG_SAVE_PATH)

DBSERVER = utils.data.database.DB(LOGGER)

SEARCHER = utils.search.searcher.Searcher()


