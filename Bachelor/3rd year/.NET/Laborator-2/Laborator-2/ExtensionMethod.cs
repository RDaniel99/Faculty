using System;

namespace Laborator_2
{
	public static class ExtensionMethod
	{
		public static int WordCount(this String str)
		{
			return str.Split(new char[] { ' ' }, StringSplitOptions.RemoveEmptyEntries).Length;
		}
	}
}
