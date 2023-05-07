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
register_info = RegisterInfo('PB20061338', 'kzw1338', '23982092040', '123456')
client.register(register_info)
img = Image.open(r'C:\Users\Administrator\Desktop\Uapp\design.png')
buffered = BytesIO()
img.save(buffered, format='PNG')
img_byte = buffered.getvalue()
for i in range(20):
    postinfo = PostInfo('PB20061338', True, img_byte, 'test'+str(i), 'test', 'test', True, 12682379484, "山地车", 1628372838, str(i))
    client.uploadPost(postinfo)
# result = client.searchNext10("山地车", 0,True, True)
# for i in result:
#     print(i)

# 关闭连接
transport.close()