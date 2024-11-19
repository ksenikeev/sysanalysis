#pip install pycryptodome #(pip install pycryptodomex - Windows)
from Crypto.PublicKey import RSA
from Crypto.Signature import pkcs1_15
from Crypto.Hash import SHA256
import binascii

# message as string
msg='{"autor":"Еникеев Камиль Шамилевич"}' # M
signature_msg = 'c1cc43eeefb1fb4fe35a98068daebd13ba7ed8be1c700c9ae8538bbc2d08f3bdb111b378f916b86102d28ed3d63ffb1f9786b2919c70f8d870f47aad4c9ec835ccb9826cffa9cb3985e85c0e95cf77ae96a4e7430e0932bb41eeb5c559e92fd9dfea81748e3a47eb0b1b134fd3907c70b6f79667bc07621c77600f5101f4d67b' #m

# load public key
f_public = open('public.key','r')
# convert hex string to bytes
pkey_hex = binascii.unhexlify(f_public.read())
# make key object from bytes
key = RSA.import_key(pkey_hex)

# new hasher and signer object for RSAwithSHA256 algorithm
hasher = SHA256.new()
signer = pkcs1_15.new(key)

# msg -> hash
hasher.update(bytearray(msg, 'utf-8'))
# sign hash
try:
    signer.verify(hasher, binascii.unhexlify(signature_msg))
    print('подпись действительна')
except ValueError:
    print('подпись недействительна')