using System;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using Laborator_2;

namespace Laborator_2Test
{
	[TestClass]
	public class ManagerTest
	{
		[TestMethod]
		public void WhenEndDateIsBeforeToday_THEN_IsActiveReturnsFalse()
		{
			Manager manager = CreateInactiveManager();

			bool IsActive = manager.IsActive();

			Assert.AreEqual(IsActive, false);
		}
		[TestMethod]
		public void WhenFirstNameIsJohnAndLastNameIsDoe_THEN_GetFullNameReturnsJohn_Doe()
		{
			Manager manager = CreateActiveManager();

			string FullName = manager.GetFullName();

			Assert.AreEqual(FullName, "John Doe");
		}
		[TestMethod]
		public void WhenFirstNameIsJohnAndLastNameIsDoeAndIsManager_THEN_SalutationReturnsHelloManagerJohnDoe()
		{
			Manager manager = CreateActiveManager();

			string Salutation = manager.Salutation();

			Assert.AreEqual(Salutation, "Hello, manager John Doe!");
		}

		Manager CreateInactiveManager()
		{
			return new Manager("John", "Doe", new DateTime(2012, 10, 8), new DateTime(2018, 1, 6), 5000);
		}
		Manager CreateActiveManager()
		{
			return new Manager("John", "Doe", new DateTime(2012, 10, 8), new DateTime(2020, 1, 6), 5000);
		}
	}
}
