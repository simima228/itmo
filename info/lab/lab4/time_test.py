import time

import jjson
import ttoml
import json_toml_with_lib

start1 = time.perf_counter()
for i in range(100):
    jjson.deserialization()
    ttoml.serialization()
end1 = time.perf_counter()
print(f"Мой код выполнен за: {end1 - start1:.8f} секунд.")
start2 = time.perf_counter()
for i in range(100):
    json_toml_with_lib.test()
end2 = time.perf_counter()
print(f"Не мой код выполнен за: {end2 - start2:.8f} секунд.")
# Мой код в 2 раза медленнее
# Мой код выполнен за: 0.08440230 секунд.
# Не мой код выполнен за: 0.04079570 секунд.
