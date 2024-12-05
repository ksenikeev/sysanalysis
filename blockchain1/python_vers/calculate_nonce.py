from Crypto.Hash import SHA256

data = "block  data"
nonce = 1

for i in range(0,100000):
    nonce_as_bytes = nonce.to_bytes (length=4, byteorder='big')
    hasher = SHA256.new()
    hasher.update(bytearray(data, 'utf-8') + nonce_as_bytes)
    h = hasher.hexdigest()
    if h[0:4] == '0000':
        print(str(nonce) + " " + h)
        break
    nonce += 1
