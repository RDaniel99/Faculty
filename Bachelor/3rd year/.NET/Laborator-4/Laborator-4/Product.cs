using System;
using System.Collections.Generic;
using System.Text;

namespace Laborator_4
{
	public class Product
	{
		private Product() { }
		public static Product Create(string description, int price)
		{
			return new Product()
			{
				Description = description,
				Price = price,
				Id = new Guid()
			};
		}
		public void UpdatePrice(int price)
		{
			Price = price;
		}
		public Guid Id { get; set; }
		public string Description { get; set; }
		public int Price { get; set; }
	}
}
