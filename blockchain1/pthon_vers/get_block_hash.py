#pip install pycryptodome #(pip install pycryptodomex - Windows)
from Crypto.Hash import SHA256
import binascii
import requests

json_data = '{"name":"Ломоносова","city":"Москва"}'
prevhash = json_chain[n-1]['prevhash']
# нормализуем JSON объект
data = str(json_chain[n-1]['data']).replace('\'','"').replace(' ','')
signature = json_chain[n-1]['signature']

hasher = SHA256.new()
# берем хэш от соединения prevhash + data + signature в бинарном представлении
hasher.update(binascii.unhexlify(prevhash) + bytearray(data, 'utf-8') + binascii.unhexlify(signature))
print(hasher.hexdigest())
