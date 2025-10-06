# Author = Trikashnyi Maksim Dmitrievich
# Group = P3114
# Date = 05.10.2025
import unittest
import Informatics_Lab3_Task2


class Test1(unittest.TestCase):
    def test1(self):
        text = 'user.name@y.com'
        result = 'y.com'
        self.assertEqual(result, Informatics_Lab3_Task2.main(text))

    def test2(self):
        text = 'ivan_ivanov123@yandex.ru'
        result = 'yandex.ru'
        self.assertEqual(result, Informatics_Lab3_Task2.main(text))

    def test3(self):
        text = 'test@server'
        result = 'Fail!'
        self.assertEqual(result, Informatics_Lab3_Task2.main(text))

    def test4(self):
        text = 'user@server123.com'
        result = 'Fail!'
        self.assertEqual(result, Informatics_Lab3_Task2.main(text))

    def test5(self):
        text = 'user-name@mail.com'
        result = 'Fail!'
        self.assertEqual(result, Informatics_Lab3_Task2.main(text))


if __name__ == '__main__':
    unittest.main()