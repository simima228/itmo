import binary


def value2toml(value):
    if isinstance(value, bool):
        return str(value).lower()
    elif isinstance(value, str):
        return f'"{value}"'
    elif isinstance(value, (int, float)):
        return str(value)
    elif value is None:
        return '"null"'
    elif isinstance(value, list):
        if not value:
            return "[]"
        first_type = type(value[0])
        if first_type == dict:
            return None
        items = [value2toml(item) for item in value]
        return f"[{', '.join(items)}]"
    return None


def dict2toml(data, current_path=(), is_array_of_dicts=False):
    lines = []
    if is_array_of_dicts:
        path = ".".join(current_path)
        lines.append(f"[[{path}]]")
    for key, value in data.items():
        full_key = current_path + (key,)
        if isinstance(value, dict):
            path_str = ".".join(full_key)
            lines.append(f"[{path_str}]")
            lines.extend(dict2toml(value, full_key))
        elif isinstance(value, list) and value and isinstance(value[0], dict):
            for item in value:
                lines.extend(dict2toml(item, full_key, is_array_of_dicts=True))
        else:
            value = value2toml(value)
            if value is not None:
                lines.append(f"{key} = {value}")

    return lines


def serialization(data=binary.binary2dict('output.bin'), filename='schedule.toml'):
    lines = dict2toml(data)
    lines.append('\n')
    with open(filename, 'w', encoding='utf-8') as f:
        for i in range(len(lines) - 1):
            f.write(lines[i] + '\n\n' if lines[i + 1][0] == '[' else lines[i] + '\n')
