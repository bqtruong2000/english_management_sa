using class_service.Models;
using static System.Runtime.InteropServices.JavaScript.JSType;

namespace class_service.ClassApi.Collections
{
    public class ClassIterator
    {
        private List<Class> _class;
        private int _current = 0;

        public ClassIterator(List<Class> currentClass)
        {
            _class = currentClass;
        }

        public bool HasNext()
        {
            return _current < _class.Count;
        }

        public Class Next()
        {
            if (!HasNext())
                throw new InvalidOperationException("No more classes");

            return _class[_current++];
        }

        public void Reset()
        {
            _current = 0;
        }

        // Filtering methods
        public IEnumerable<Class> FilterByRoom(string room)
        {
            Reset();
            while (HasNext())
            {
                var currentClass = Next();
                if (currentClass.Room.Equals(room, StringComparison.OrdinalIgnoreCase))
                    yield return currentClass;
            }
        }

    }
}
