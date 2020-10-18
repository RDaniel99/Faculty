using Microsoft.VisualStudio.TestTools.UnitTesting;
using Laborator_2;
using System;

namespace Laborator_2Test
{
	[TestClass]
	public class ArchitectTest
	{
		[TestMethod]
		public void WhenEndDateIsBeforeToday_THEN_IsActiveReturnsFalse()
		{
			Architect architect = CreateInactiveArchitect();

			bool IsActive = architect.IsActive();

			Assert.AreEqual(IsActive, false);
		}
		[TestMethod]
		public void WhenFirstNameIsJohnAndLastNameIsDoe_THEN_GetFullNameReturnsJohn_Doe()
		{
			Architect architect = CreateActiveArchitect();

			string FullName = architect.GetFullName();

			Assert.AreEqual(FullName, "John Doe");
		}
		[TestMethod]
		public void WhenFirstNameIsJohnAndLastNameIsDoeAndIsArchitect_THEN_SalutationReturnsHelloArchitectJohnDoe()
		{
			Architect architect = CreateActiveArchitect();

			string Salutation = architect.Salutation();

			Assert.AreEqual(Salutation, "Hello, architect John Doe!");
		}

		Architect CreateInactiveArchitect()
		{
			return new Architect("John", "Doe", new DateTime(2012, 10, 8), new DateTime(2018, 1, 6), 5000);
		}
		Architect CreateActiveArchitect()
		{
			return new Architect("John", "Doe", new DateTime(2012, 10, 8), new DateTime(2020, 1, 6), 5000);
		}
	}
}
