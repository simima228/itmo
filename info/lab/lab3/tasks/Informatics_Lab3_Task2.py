# Author = Trikashnyi Maksim Dmitrievich
# Group = P3114
# Date = 05.10.2025
import re


def main(a):
    pat = r'^[a-zA-Z0-9._]+@[a-zA-Z.]+\.[a-zA-Z]+$'
    if re.match(pat, a) is not None:
        return a.split('@')[1]
    else:
        return 'Fail!'