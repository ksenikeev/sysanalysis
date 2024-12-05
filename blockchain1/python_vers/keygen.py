#pip install pycryptodome #(pip install pycryptodomex - Windows)
from Crypto.PublicKey import RSA
import binascii

# Генерируем ключевую пару
key = RSA.generate(1024)
privk = key.export_key('DER', pkcs=8)
pubk = key.publickey().export_key('DER', pkcs=8)
print('private_hex=' + str(binascii.hexlify(privk)))
print('public_hex=' + str(binascii.hexlify(pubk)))

# save private key as hex
f_private = open('private.key','wb')
f_private.write(binascii.hexlify(privk))
f_private.close()

# save public key as hex
f_public = open('public.key','wb')
f_public.write(binascii.hexlify(pubk))
f_public.close()

# load private key from file
f_private = open('rsa.key','r')
keystr = binascii.unhexlify(f_private.read())
key = RSA.import_key(keystr)
