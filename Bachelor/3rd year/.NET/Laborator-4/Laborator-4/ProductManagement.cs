using System;
using System.Collections.Generic;
using System.Text;
using DbContext = Microsoft.EntityFrameworkCore.DbContext;
using Microsoft.EntityFrameworkCore;
using System.Linq;

namespace Laborator_4
{
    public sealed class ProductManagement : DbContext
    {
        protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder)
        {
            if (!optionsBuilder.IsConfigured)
            {
                optionsBuilder.UseSqlServer(@"Server=(localdb)\mssqllocaldb;Database=Lab04DB;Trusted_Connection=True;");
            }
        }
        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            modelBuilder.Entity<Product>().ToTable("Products");
            modelBuilder.Entity<Product>(
                p =>
                {
					p.Property(e => e.Id);
                    p.Property(e => e.Description);
                    p.Property(e => e.Price);
                });
			Seed(modelBuilder);
        }
		private void Seed(ModelBuilder modelBuilder)
		{
			modelBuilder.Entity<Product>().HasData(Product.Create("mere", 5), Product.Create("zahar", 2), Product.Create("paine", 3), Product.Create("masina", 150000));
		}
		public void Create(Product product)
		{
			Products.Add(product);
			SaveChanges();
		}
		public IQueryable<Product> GetAll()
		{ 
			return Products;
		}
		public IQueryable<Product> GetProductsMoreExpensive(int price)
		{
			return Products.Where(p => p.Price >= price);
		}
		public Product GetById( Guid id )
		{
			return Products.First(p => p.Id == id);
		}
		public IQueryable<Product> GetProductsBetween(int lowerPrice, int higherPrice)
		{
			return Products.Where(p => p.Price >= lowerPrice && p.Price <= higherPrice);
		}
		public void Update(Product product, int price)
		{
			product.UpdatePrice(price);
			Products.Update(product);
			SaveChanges();
		}
		public void Delete(Guid id)
		{
			Products.Remove(Products.First(p => p.Id == id));
			SaveChanges();
		}
		public Microsoft.EntityFrameworkCore.DbSet<Product> Products { get; set; }
	}
}
