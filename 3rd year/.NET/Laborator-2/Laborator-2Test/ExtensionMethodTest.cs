using Microsoft.VisualStudio.TestTools.UnitTesting;
using Laborator_2;

namespace Laborator_2Test
{
	[TestClass]
	public class ExtensionMethodTest
	{
		[TestMethod]
		public void WhenAnaAreMere_THEN_WordCountReturns3()
		{
			string s = "Ana are mere";

			int wordCount = s.WordCount();

			Assert.AreEqual(wordCount, 3);
		}
		[TestMethod]
		public void WhenEmptySentence_THEN_WordCountReturns0()
		{
			string s = "";

			int WordCount = s.WordCount();

			Assert.AreEqual(WordCount, 0);
		}
	}
}
