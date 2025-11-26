import json
import toml


def test():
    with open('test.toml', 'w', encoding='utf-8') as f:
        data = json.load(open('schedule.json', 'r', encoding='utf-8'))
        f.write(toml.dumps(data))