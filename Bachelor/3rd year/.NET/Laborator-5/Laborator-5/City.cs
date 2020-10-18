using System;
using Microsoft.EntityFrameworkCore;
using System.Collections.Generic;

namespace Laborator_5
{
	public class City
	{
		private City() { }
		public static City Create(string name, string region)
		{
			return new City()
			{
				ID = new Guid(),
				CityName = name,
				Region = region,
				Points = new HashSet<PointOfInterest>()
			};
		}
		public Guid ID { get; private set; }
		public string CityName { get; private set; }
		public string Region { get; private set;  }
		public ICollection<PointOfInterest> Points { get; private set; }
		public void UpdateName(string name)
		{
			CityName = name;
		}
	}
}
