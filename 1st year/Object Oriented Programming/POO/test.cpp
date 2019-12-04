#include "stdafx.h"
#include "ShoppingList.h"
#include "Food.h"
#include "Miscellaneous.h"

int main() {
	Food item1;
	item1.setName("meat");
	item1.setQuantity(1.5f);
	Miscellaneous item2;
	item2.setName("servetele");
	item2.setCount(3);
	Food item3;
	item3.setName("cartofi");
	item3.setQuantity(4.6f);
	ShoppingList L;
	L.addItem(&item1);
	L.addItem(&item2);
	L.addItem(&item3);
	L.printList();
	return 0;
}