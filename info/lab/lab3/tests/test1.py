# Author = Trikashnyi Maksim Dmitrievich
# Group = P3114
# Date = 05.10.2025
import unittest
import sys

sys.path.insert(0, os.path.abspath(os.path.join(os.path.dirname(__file__), '..')))


from tasks import Informatics_Lab3_Task1


class Test1(unittest.TestCase):
    def test1(self):
        text = 'Старый пруд замолк / Лягушка прыгнула в тишь / Всплеск воды слышен'
        result = 'Хайку!'
        self.assertEqual(result, Informatics_Lab3_Task1.main(text))

    def test2(self):
        text = 'Осенний вечер / Тихо падают листья '
        result = 'Не хайку. Должно быть 3 строки.'
        self.assertEqual(result, Informatics_Lab3_Task1.main(text))

    def test3(self):
        text = 'Холодный ветер дует / Листья кружатся в танце / Осень пришла'
        result = 'Не хайку.'
        self.assertEqual(result, Informatics_Lab3_Task1.main(text))

    def test4(self):
        text = '/'
        result = 'Не хайку. Должно быть 3 строки.'
        self.assertEqual(result, Informatics_Lab3_Task1.main(text))

    def test5(self):
        text = '/ /'
        result = 'Не хайку.'
        self.assertEqual(result, Informatics_Lab3_Task1.main(text))


if __name__ == '__main__':
    unittest.main()


