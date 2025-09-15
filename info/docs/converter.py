import string


def euclid(num, base):
    n = num
    alp = string.printable[:base].upper()
    res = ''
    while n:
        res += alp[n % base]
        n //= base
    return res[::-1]


def convert_float(ost, base, n=16):
    b = ''
    alp = string.printable[:base].upper()
    while n:
        ost *= base
        ost = round(ost, n)
        b += str(alp[int(ost)])
        ost -= int(ost)
        n -= 1
    return b


def convert(float_number, base1, base2):
    alp = string.printable[:max(base1, base2)].upper()
    num, ost = map(str, float_number.split('.'))
    a = euclid(int(num, base1), base2)
    b = 0
    k = base1
    for i in ost:
        b += alp.index(i) / k
        k *= base1
    b = str(convert_float(b, base2)).rstrip('0')
    return f'{a}.{b}'


def main():
    number = input('Введите число: ')
    base1 = int(input('Введите систему счисления: '))
    base2 = int(input('Введите желаемую систему счисления: '))
    return convert(number, base1, base2) if '.' in number else euclid(int(number, base1), base2)


if __name__ == '__main__':
    print(main())
