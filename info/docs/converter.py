def main():
    number = input('Введите число: ')
    fib = [1, 1]
    res = 0
    for i in range(len(number)):
        fib.append(fib[i] + fib[i + 1])
    fib = fib[1:-1]
    for i in range(len(number)):
        res += int(number[-i - 1]) * fib[i]
    return res


if __name__ == '__main__':
    print(main())
