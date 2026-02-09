import struct


def dict2binary(data, filename):
    def write_string(file, string):
        string_bytes = string.encode('utf-8')
        file.write(len(string_bytes).to_bytes(4, 'big'))
        file.write(string_bytes)

    def write_value(file, value):
        if isinstance(value, bool):
            file.write(b'B')
            file.write(b'\x01' if value else b'\x00')
        elif isinstance(value, str):
            file.write(b'S')
            write_string(file, value)
        elif isinstance(value, int):
            file.write(b'I')
            file.write(value.to_bytes(8, 'big', signed=True))
        elif isinstance(value, float):
            file.write(b'F')
            file.write(struct.pack('d', value))
        elif isinstance(value, list):
            file.write(b'L')
            file.write(len(value).to_bytes(4, 'big'))
            for item in value:
                write_value(f, item)
        elif isinstance(value, dict):
            file.write(b'D')
            write_dict(file, value)
        elif value is None:
            file.write(b'N')

    def write_dict(file, dict_data):
        f.write(len(dict_data).to_bytes(4, 'big'))
        for key, value in dict_data.items():
            write_string(file, str(key))
            write_value(file, value)

    with open(filename, 'wb') as f:
        write_dict(f, data)


def binary2dict(filename):
    def read_string(file):
        length_bytes = file.read(4)
        if not length_bytes or len(length_bytes) < 4:
            return None
        length = int.from_bytes(length_bytes, 'big')
        string_bytes = file.read(length)
        return string_bytes.decode('utf-8')

    def read_value(file):
        type_byte = file.read(1)
        if not type_byte:
            return None
        if type_byte == b'B':
            return bool(f.read(1)[0])
        elif type_byte == b'S':
            return read_string(file)
        elif type_byte == b'I':
            value_bytes = file.read(8)
            return int.from_bytes(value_bytes, 'big', signed=True)
        elif type_byte == b'F':
            value_bytes = file.read(8)
            return struct.unpack('d', value_bytes)[0]
        elif type_byte == b'L':
            length_bytes = f.read(4)
            if not length_bytes:
                return []
            length = int.from_bytes(length_bytes, 'big')
            return [read_value(file) for _ in range(length)]
        elif type_byte == b'D':
            return read_dict(file)
        elif type_byte == b'N':
            return None

    def read_dict(file):
        data = {}
        count_bytes = file.read(4)
        if not count_bytes:
            return {}
        count = int.from_bytes(count_bytes, 'big')

        for i in range(count):
            key = read_string(file)
            value = read_value(file)
            if key is not None:
                data[key] = value

        return data

    with open(filename, 'rb') as f:
        return read_dict(f)
