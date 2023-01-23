class Card:


    noms_couleurs = ['trèfle', 'carreau', 'cœur', 'pique']
    noms_valeurs = ['as', '2', '3', '4', '5', '6', '7',
                '8', '9', '10', 'valet', 'dame', 'roi']


    def __init__(self, symbol, value):
        self.symbol = symbol
        self.value = value
        if self.symbol == 2 or self.symbol == 1:
            self.color = "Red"

    def __repr__(self):
        return f"{self.noms_couleurs[self.symbol]} of {self.noms_valeurs[self.value]} and colored"


listCard = []
for y in range (0,4):
    for i in range (0,13):
        listCard.append(Card(y,i))

print(listCard)

