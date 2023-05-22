from thrift.transport import TSocket
from thrift.transport import TTransport
from thrift.protocol import TBinaryProtocol
from urpc import UappService
from io import BytesIO
from PIL import Image
from urpc.ttypes import *

# 创建 Thrift 客户端
transport = TSocket.TSocket('localhost', 7860) # 创建 TSocket 对象，指定服务器的地址和端口
transport = TTransport.TBufferedTransport(transport) # 创建 TTransport 对象，用于封装 TSocket 对象
protocol = TBinaryProtocol.TBinaryProtocol(transport) # 创建 TProtocol 对象，用于序列化和反序列化数据
client = UappService.Client(protocol) # 创建 ExampleService.Client 对象，用于调用远程接口

# 打开连接
transport.open()

# 调用远程接口
# register_info = RegisterInfo('PB20061338', 'kzw1338', '23982092040', '123456')
# client.register(register_info)
# img = Image.open(r'C:\Users\Administrator\Desktop\Uapp\design.jpg')
# buffered = BytesIO()
# img.save(buffered, format='JPEG')
# img_byte = buffered.getvalue()
# post_ids = ["FjWiZ_uh9A","7BdDl_Hz_K","Y3vKv8_9fL","yD2_uJc4z8","6wT_9VhZrJ","5X2D_gk7tM","EJ6q_hR5zK","vE8_2sLjWx","4KfS_mT7zN","bG9_Qy5fjA","3Tn_vh8QdS","yB6_Gf4tJZ","2Xh_Dv9cLs","8Rz_Ns7dCv","Vj6_gH9bTf","Lk3_uF7mZp","Jh9_bV6nWr","Kp4_kT5gFq","6Sd_Hv8jMz","Zx2_rT7pLq"]
# for i in range(20):
#     postinfo = PostInfo(post_ids[i],'PB20061338', True, img_byte, 'test.jpg'+str(i), 'test', 'test', True, 12682379484, "山地车", 1628372838)
#     client.uploadPost(postinfo)
result = client.searchNext10("山地车", "",True, True)
for i in result:
    print(i)

# 关闭连接
transport.close()

