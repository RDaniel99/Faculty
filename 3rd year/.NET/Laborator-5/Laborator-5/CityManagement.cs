using System;
using System.Collections.Generic;
using System.Text;
using System.Linq;

namespace Laborator_5
{
	class CityManagement
	{
		public IQueryable<City> GetAll()
		{
			Database db = new Database();
			return db.Cities;
		}
		public City GetById(Guid id)
		{
			Database db = new Database();
			return db.Cities.First(city => city.ID == id);
		}
		public void Create(City city)
		{
			Database db = new Database();
			db.Cities.Add(city);
			db.SaveChanges();
		}
		public void Update(Guid id, string name)
		{
			Database db = new Database();
			City city = db.Cities.First(city => city.ID == id);
			city.UpdateName(name);
			db.Cities.Update(city);
			db.SaveChanges();
		}
		public void Delete(Guid id)
		{
			Database db = new Database();
			City city = db.Cities.First(city => city.ID == id);
			if (db.PointsOfInterest.FirstOrDefault(point => point.City == city) == null)
			{
				db.Cities.Remove(city);
				db.SaveChanges();
			}
		}
	}
}
