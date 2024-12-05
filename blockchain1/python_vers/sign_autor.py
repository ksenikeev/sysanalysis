#pip install pycryptodome #(pip install pycryptodomex - Windows)
from Crypto.PublicKey import RSA
from Crypto.Signature import pkcs1_15
from Crypto.Hash import SHA256
import binascii

# message as string
msg='{"autor":"Еникеев Камиль Шамилевич"}'

# load private key
f_private = open('private.key','r')
privkey = binascii.unhexlify(f_private.read())
key = RSA.import_key(privkey)

# new hasher and signer object for RSAwithSHA256 algorithm
hasher = SHA256.new()
signer = pkcs1_15.new(key)

# msg -> hash
hasher.update(bytearray(msg, 'utf-8'))
# sign hash
signed_msg  = signer.sign(hasher)

print(binascii.hexlify(signed_msg))