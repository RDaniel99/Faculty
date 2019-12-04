using System;
using System.Collections.Generic;
using System.Text;

namespace Laborator_5
{
	public class PointOfInterest
	{
		private PointOfInterest() { }
		public static PointOfInterest Create(string name, double longitude, double latitude, City city)
		{
			PointOfInterest thisPoint = new PointOfInterest()
			{
				ID = new Guid(),
				City = city,
				Name = name,
				Longitude = longitude,
				Latitude = latitude
			};
			city.Points.Add(thisPoint);
			return thisPoint;
		}
		public Guid ID { get; private set; }
		public string Name { get; private set; }
		public double Longitude { get; private set; }
		public double Latitude { get; private set; }
		public City City { get; private set; }
	}
}
