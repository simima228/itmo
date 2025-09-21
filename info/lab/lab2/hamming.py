from math import log


def decode(bits):
    first = bits[:]
    for i in range(int(log(len(bits) + 1, 2))):
        first[2 ** i - 1] = '0'
    for i in range(int(log(len(bits) + 1, 2))):
        n = 0
        for j in range(0, len(bits), 2 ** (i + 1)):
            n += sum(map(int, first[2 ** i - 1:][j:j + 2 ** i]))
        first[2 ** i - 1] = str(n % 2)
    return first


def main():  # работает для классического кода хэмминга любой длины, примеры: (7,4): 0110011, (15, 11): 011001110010110
    code = list(input('Введите сообщение: '))
    dec = decode(code)
    if dec != code:
        n = 0
        for i in range(len(dec)):
            if dec[i] != code[i]:
                n += i + 1
        code[n - 1] = str((int(code[n - 1]) + 1) % 2)
        print(f'В сообщении была ошибка на {n}-ом бите, правильный вариант: {"".join(code)}')
    else:
        print('Ошибок нет')
    for i in range(int(log(len(code) + 1, 2))):
        code[2 ** i - 1] = '-'
    code = ''.join(code).split('-')
    print("Информационные биты:", ''.join(code))


if __name__ == '__main__':
    main()
