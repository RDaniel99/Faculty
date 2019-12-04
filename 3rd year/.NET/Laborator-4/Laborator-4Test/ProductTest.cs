using Microsoft.VisualStudio.TestTools.UnitTesting;
using Laborator_4;
namespace Laborator_4Test
{
	[TestClass]
	public class ProductTest
	{
		[TestMethod]
		public void CreateTest()
		{
			Product product = Product.Create("mere",10);
			Assert.AreEqual(product.Description, "mere");
			Assert.AreEqual(product.Price, 10);
			Assert.IsNotNull(product.Id);
		}
		[TestMethod]
		public void UpdateTest()
		{
			Product product = Product.Create("mere", 10);
			product.UpdatePrice(14);
			Assert.AreEqual(product.Price, 14);
		}
	}
}
