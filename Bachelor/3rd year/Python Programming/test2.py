import os
import stat
import re
import hashlib
import zipfile


def problema1(my_list):
    maxim = 0
    sir_max = ""
    for sir in my_list:
        if len(sir) > maxim:
            maxim = len(sir)
            sir_max = sir
    return sir_max


def problema2(the_path):
    l = os.listdir(the_path)
    my_list = []
    for file in l:
        file = os.path.join(the_path, file)
        st = os.stat(file).st_mode
        if stat.S_ISDIR(st) == 0:
            content = open(file, "r").read()
            reversed_content = ""
            for c in content:
                reversed_content = c + reversed_content
            if content == reversed_content:
                my_list.append(file)
    return my_list


def problema3(dir_path):
    dir_path = os.path.abspath(dir_path)
    my_list = []
    for root, dirs, files in os.walk(dir_path):
        for name in dirs:
            if len(re.findall('[0-9][0-9][0-9]', name)) > 0:
                my_list.append(os.path.join(root, name))
    return my_list


def problema4(the_path, hash_to_find):
    _zip = zipfile.ZipFile(the_path, 'r')
    for name in _zip.namelist():
        file = _zip.open(name, 'r')
        content = file.read()
        hash_found = hashlib.md5()
        hash_found.update(content)
        if hash_found.hex_digest() == hash_to_find:
            return len(content)

hash_v=hashlib.md5("ascsacsav".encode()).hexdigest()
print(hash_v)
