using System;

namespace Laborator_2
{
	public abstract class Employee
	{
		public Guid Id { get; private set; }
		public string FirstName { get; private set; }
		public string LastName { get; private set; }
		public DateTime StartDate { get; private set; }
		public DateTime EndDate { get; private set; }
		public int Salary { get; private set; }
		public Employee(string firstName, string lastName, DateTime startDate, DateTime endDate, int salary)
		{
			FirstName = firstName;
			LastName = lastName;
			StartDate = startDate;
			EndDate = endDate;
			Salary = salary;
			Id = Guid.NewGuid();
		}
		public string GetFullName()
		{
			return FirstName + " " + LastName;
		}
		public bool IsActive()
		{
			return EndDate >= DateTime.Today;
		}
		abstract public string Salutation();
	}
}
