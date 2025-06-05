from django.shortcuts import render
from Crypto.Cipher import DES
from Crypto.Util.Padding import pad, unpad
from Crypto.Random import get_random_bytes
import binascii
import base64

# DES key must be 8 bytes
KEY = b'8bytekey'  # Replace with your own secure key, 8 bytes exactly

def des_tool_view(request):
    context = {}

    if request.method == 'POST':
        message = request.POST.get('message', '').strip()
        action = request.POST.get('action')

        if not message:
            context['error'] = "Please enter a message."
            return render(request, 'des_form.html', context)

        if action not in ('encrypt', 'decrypt'):
            context['error'] = "Invalid action."
            return render(request, 'des_form.html', context)

        try:
            if action == 'encrypt':
                # Generate a random 8-byte IV for each encryption
                iv = get_random_bytes(8)

                des = DES.new(KEY, DES.MODE_CBC, iv)
                padded_text = pad(message.encode(), DES.block_size)
                encrypted_bytes = des.encrypt(padded_text)

                # Prepend IV to the ciphertext for use in decryption
                encrypted_with_iv = iv + encrypted_bytes

                # Encode as hex string for display
                encrypted_hex = binascii.hexlify(encrypted_with_iv).decode()

                context['message'] = message
                context['encrypted'] = encrypted_hex

            else:  # decrypt
                # Input is hex string with IV + ciphertext
                try:
                    encrypted_with_iv = binascii.unhexlify(message)

                    # Extract IV and ciphertext
                    iv = encrypted_with_iv[:8]
                    ciphertext = encrypted_with_iv[8:]

                    des = DES.new(KEY, DES.MODE_CBC, iv)
                    decrypted_padded = des.decrypt(ciphertext)
                    decrypted = unpad(decrypted_padded, DES.block_size).decode()

                    context['message'] = message
                    context['decrypted'] = decrypted
                except (binascii.Error, ValueError) as e:
                    context['error'] = "Invalid encrypted input or padding error."

        except Exception as e:
            context['error'] = f"Encryption/Decryption error: {str(e)}"

    return render(request, 'des_form.html', context)
