package bot

def facebook = new MessengerBot()

def userId = 1094997037238723

facebook.handleMessage userId, "help"
