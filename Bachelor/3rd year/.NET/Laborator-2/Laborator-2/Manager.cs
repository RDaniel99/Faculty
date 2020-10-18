using System;

namespace Laborator_2
{
	public class Manager : Employee
	{
		public Manager(string firstName, string lastName, DateTime startDate, DateTime endDate, int salary) : base(firstName, lastName, startDate, endDate, salary)
		{
		}

		public override string Salutation()
		{
			return "Hello, manager " + GetFullName() + "!";
		}
	}
}
