using System;
using System.Collections.Generic;
using System.Text;
using System.Linq;

namespace Laborator_5
{
	class PointsOfInterestManagement
	{
		public void Create(PointOfInterest point, City city)
		{
			Database db = new Database();
			City inDB = db.Cities.First(City => City.ID == city.ID);
			inDB.Points.Add(point);
			CityManagement citiesManagement = new CityManagement();
			citiesManagement.Delete(inDB.ID);
			citiesManagement.Create(inDB);
			db.SaveChanges();
		}
	}
}
