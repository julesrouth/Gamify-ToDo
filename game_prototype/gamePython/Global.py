from colorama import Fore, Back, Style
ENABLE_CLR = True


LINE_LENGTH = 80
LINELENGTH = 80
def clearScreen():
    if ENABLE_CLR:
        print("\033c")

def printColoredString(inputString: str) -> str:
    inputString = str(inputString)
    for char in inputString:
        if char == '0':
            print(Fore.GREEN + char, end='')
        elif char == '1':
            print(Fore.BLUE + char, end='')
        elif char == '2':
            print(Fore.WHITE + char, end='')
        else:
            print(Fore.RED + char, end='')


