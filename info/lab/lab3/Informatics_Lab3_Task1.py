# Author = Trikashnyi Maksim Dmitrievich
# Group = P3114
# Date = 05.10.2025
import re


def count(st, n):
    pat = r'[аеёиоуыэюя]'
    if len(re.findall(pat, st.lower())) == n:
        return True
    return False


def main(a):
    if a.count('/') != 2:
        return 'Не хайку. Должно быть 3 строки.'
    else:
        s = a.split("/")
        if all([count(i, j) for i, j in zip(s, [5, 7, 5])]) is True:
            return 'Хайку!'
        else:
            return 'Не хайку.'
