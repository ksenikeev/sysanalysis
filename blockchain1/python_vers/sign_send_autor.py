#pip install pycryptodome #(pip install pycryptodomex - Windows)
from Crypto.PublicKey import RSA
from Crypto.Signature import pkcs1_15
from Crypto.Hash import SHA256
import binascii
import requests

# message as string
autor='Еникеев Камиль Шамилевич 11-102'

# load private key
f_private = open('private.key','r')
privkey = binascii.unhexlify(f_private.read())
key = RSA.import_key(privkey)

# new hasher and signer object for RSAwithSHA256 algorithm
hasher = SHA256.new()
signer = pkcs1_15.new(key)

# sign
hasher.update(bytearray(autor, 'utf-8'))
signed_msg  = signer.sign(hasher).hex()

# load public key
pub_key_str = open('public.key','r').read()

# send autor
url = 'http://itislabs.ru/nbc/autor'
payload = {"autor":autor,"sign":signed_msg,"publickey":pub_key_str}
print(payload)
headers = {'content-type': 'application/json;charset=UTF-8'}
response = requests.post(url, json=payload, headers=headers)

print(response.text)