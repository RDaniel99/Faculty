using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Text;
using DbContext = Microsoft.EntityFrameworkCore.DbContext;

namespace Laborator_5
{
	public class Database: DbContext
	{
		public Microsoft.EntityFrameworkCore.DbSet<City> Cities { get; set; }
		public Microsoft.EntityFrameworkCore.DbSet<PointOfInterest> PointsOfInterest { get; set; }
		protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder)
		{
			if (!optionsBuilder.IsConfigured)
			{
				optionsBuilder.UseSqlServer(@"Server=(localdb)\mssqllocaldb;Database=Lab05DB;Trusted_Connection=True;");
			}
		}
		protected override void OnModelCreating(ModelBuilder modelBuilder)
		{
			modelBuilder.Entity<City>().ToTable("Cities");
			modelBuilder.Entity<City>(
				p =>
				{
					p.Property(e => e.CityName);
					p.Property(e => e.ID);
					p.Property(e => e.Points);
					p.Property(e => e.Region);

				});

			modelBuilder.Entity<PointOfInterest>().ToTable("PointsOfInterest");
			modelBuilder.Entity<PointOfInterest>(
				c =>
				{
					c.Property(f => f.ID);
					c.Property(f => f.City);
					c.Property(f => f.Latitude);
					c.Property(f => f.Longitude);
					c.Property(f => f.Name);
				});
			Seed(modelBuilder);
		}
		private void Seed(ModelBuilder modelBuilder)
		{
			City city1 = City.Create("Iasi", "ROU"),
				city2 = City.Create("Cluj", "ROU"),
				city3 = City.Create("London", "GBR");
			modelBuilder.Entity<City>().HasData(
				city1,
				city2,
				city3);
			modelBuilder.Entity<PointOfInterest>().HasData(
				PointOfInterest.Create("Palatul Culturii", 9.5, 10.5, city1),
				PointOfInterest.Create("Cluj Arena", 1392.23, 12842.56, city2),
				PointOfInterest.Create("BigBen", 1874215.569, 8469312.263, city3),
				PointOfInterest.Create("O2 Arena", 18747845.85, 8469748.8475, city3));
		}
	}
}
