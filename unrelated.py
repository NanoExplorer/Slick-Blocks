import fileinput

count = 5
for line in fileinput.input():
    if fileinput.lineno() == 1:
        count = int(line.rstrip("\n"))
    else:
        if count > 0:
            print("Hello, " + line.rstrip("\n") + "!")
            count -= 1
