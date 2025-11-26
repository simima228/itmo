from binary import dict2binary


def convert(value):
    value = value.strip()
    try:
        return int(value)
    except ValueError:
        pass
    try:
        return float(value)
    except ValueError:
        pass
    if value == 'false':
        return False
    elif value == 'true':
        return True
    elif value == 'null':
        return None
    return value[1:-1] if len(value) > 1 else value


class JSON:
    def __init__(self, filename):
        self.file = []
        for i in open(filename, 'r', encoding='utf-8').readlines():
            self.file.append(i.strip() if i.strip()[-1] != ',' else i.strip()[:-1])
        self.index = 0
        self.len = len(self.file)

    def json2dict(self):
        json_dct = {}
        while self.index < self.len:
            self.index += 1
            if self.file[self.index] == '}':
                return json_dct
            key, val = self.file[self.index].split(': ')
            key, val = convert(key), convert(val)
            if val == '{':
                json_dct[key] = self.json2dict()
            elif val == '[':
                json_dct[key] = self.json2list()
            else:
                json_dct[key] = val

    def json2list(self):
        json_list = []
        while self.index < self.len:
            self.index += 1
            if self.file[self.index] == ']':
                return json_list
            elif self.file[self.index] == '[':
                json_list.append(self.json2list())
            elif self.file[self.index] == '{':
                json_list.append(self.json2dict())
            else:
                json_list.append(convert(self.file[self.index]))


def deserialization(json_filename='schedule.json', binary_filename='output.bin'):
    parser = JSON(json_filename)
    dict2binary(parser.json2dict(), binary_filename)
