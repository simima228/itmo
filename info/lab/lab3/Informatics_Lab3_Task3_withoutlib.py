# Author = Trikashnyi Maksim Dmitrievich
# Group = P3114
# Date = 05.10.2025
import re


def change_ad(text, index):
    p_pat = r'[^\w\s(^\-)]'
    end_pat = r'(ый|ой|ий|ого|его|ому|ему|ым|им|ом|ем|ая|яя|ую|ой|ей|ое|ее|ые|ие|ых|их|ыми|ими)$'
    ad_pat = r'^(\w*)\-*(\w*)(ый|ой|ий|ого|его|ому|ему|ым|им|ом|ем|ая|яя|ую|ой|ей|ое|ее|ые|ие|ых|их|ыми|ими)$'
    ad_pat1 = r'\b[а-яА-Я\-]+((?:ый|ий|ой|ая|яя|ое|ее|ые|ие|ого|его|ому|ему|ым|им|ом|ем|ую|ой|ей|ых|их|ыми|ими))\b'
    ads = list(filter(None, [re.sub(p_pat, '', i) if re.match(ad_pat1, i) else None for i in text.split()]))
    if not ads:
        return text
    base_ads = []
    for i in ads:
        if '-' not in i:
            base_ads.append(re.sub(ad_pat, lambda x: x.group(1) + '$', i))
        else:
            t = i.split('-')
            base_ads.append(t[0] + '-' + re.sub(ad_pat, lambda x: x.group(1), t[1]))
    print(ads)
    repl_ads = {}
    for i in range(len(base_ads)):
        w = base_ads[i].lower()
        if w not in repl_ads:
            repl_ads[w] = []
        repl_ads[w].append(ads[i])
    if len(sorted(repl_ads.values(), key=len, reverse=True)[0]) < index:
        return text
    result = text
    for w in repl_ads.keys():
        if '-' in w:
            if len(repl_ads[w]) >= index:
                target = repl_ads[w][index - 1]
            else:
                target = repl_ads[w][0]
            target_root = w
            target_end = re.search(end_pat, target).group(0)
            pat = rf'\b({re.escape(target_root)})(\w*)\b'
            result = re.sub(pat, lambda x: '␟' + x.group(1) + target_end + '␟', result, flags=re.IGNORECASE)
    for w in repl_ads.keys():
        if len(repl_ads[w]) > 1 and len(repl_ads[w]) >= index:
            if '-' not in w:
                target = repl_ads[w][index - 1]
                target_end = re.search(end_pat, target).group(0)
                target_root = re.search(ad_pat, target).group(1)
                pat = rf'(?<!␟)\b({re.escape(target_root)})(\w*)\b(?!\s*␟)'
                result = re.sub(pat, lambda x: x.group(1) + target_end, result, flags=re.IGNORECASE)
    return result.replace('␟', '')


def main(st, text):
    return change_ad(text, st)
