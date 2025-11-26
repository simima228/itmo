import binary


def dict2xml_rec(data, parent_name, current_indent="", indent='\t'):
    result = ""
    if isinstance(data, dict):
        result += f"{current_indent}<{parent_name}>\n"
        for key, value in data.items():
            result += dict2xml_rec(value, key, current_indent + indent, indent)
        result += f"{current_indent}</{parent_name}>\n"
    elif isinstance(data, list):
        for item in data:
            result += dict2xml_rec(item, parent_name, current_indent, indent)
    else:
        result += f"{current_indent}<{parent_name}>{str(data)}</{parent_name}>\n"
    return result


def dict2xml(d, root_name="root", indent="\t"):
    xml = dict2xml_rec(d, root_name, "", indent)
    return xml


def xml2file(data, filename, root_name="root"):
    lines = dict2xml(data, root_name)
    with open(filename, 'w', encoding='utf-8') as f:
        f.write('<?xml version="1.0" encoding="UTF-8"?>\n')
        f.write(lines)


xml2file(binary.binary2dict('output.bin'), 'schedule.xml')
