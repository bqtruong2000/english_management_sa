using profile_service.Models;

namespace profile_service.ProfileApi.Collections
{
    public class ProfileIterator
    {
        private List<Profile> _profiles;
        private int _current = 0;

        public ProfileIterator(List<Profile> profiles)
        {
            _profiles = profiles;
        }

        public bool HasNext()
        {
            return _current < _profiles.Count;
        }

        public Profile Next()
        {
            if (!HasNext())
                throw new InvalidOperationException("No more profiles");

            return _profiles[_current++];
        }

        public void Reset()
        {
            _current = 0;
        }

        // Filtering methods
        public IEnumerable<Profile> FilterByClass(string className)
        {
            Reset();
            while (HasNext())
            {
                var profile = Next();
                if (profile._class.Equals(className, StringComparison.OrdinalIgnoreCase))
                    yield return profile;
            }
        }

        public IEnumerable<Profile> FilterByEmail(string domain)
        {
            Reset();
            while (HasNext())
            {
                var profile = Next();
                if (profile.Email?.EndsWith(domain, StringComparison.OrdinalIgnoreCase) == true)
                    yield return profile;
            }
        }
    }
}
