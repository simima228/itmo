def convert(filename='schedule.json'):
    return eval(''.join(open(filename, 'r', encoding='utf-8').readlines()
                        ).replace('true', 'True').replace('false', 'False').replace('null', 'None'))