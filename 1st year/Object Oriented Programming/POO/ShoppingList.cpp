#include "stdafx.h"
#include "ShoppingList.h"
#include "Food.h"


ShoppingList::ShoppingList() {}


ShoppingList::~ShoppingList() {}

void ShoppingList::addItem(Item* i) {
	v.push_back(i);
}

void ShoppingList::printList() {
	for (int i = 0; i < v.size(); i++) {
		cout << v[i]->getName() << " : " << v[i]->getInfo() << "; ";
	}
}
