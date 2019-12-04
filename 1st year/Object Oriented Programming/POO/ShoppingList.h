#pragma once
#include "stdafx.h"
#include "Item.h"
class ShoppingList
{
	vector <Item *> v;
public:
	ShoppingList();
	~ShoppingList();
	void addItem(Item*);
	void printList();
};

