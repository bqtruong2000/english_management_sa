using profile_service.Models;

namespace profile_service.ProfileApi.Factories
{
    public interface IProfileFactory
    {
        Profile CreateProfile(string type);
    }

    // Concrete Factory Implementation
    public class ProfileFactory : IProfileFactory
    {
        public Profile CreateProfile(string type)
        {
            return type.ToLower() switch
            {
                "student" => new Profile
                {
                    _class = "Student",
                    Code = GenerateStudentCode()
                },
                "employee" => new Profile
                {
                    _class = "Employee",
                    Code = GenerateEmployeeCode()
                },
                "external" => new Profile
                {
                    _class = "External",
                    Code = GenerateExternalCode()
                },
                _ => throw new ArgumentException("Invalid profile type")
            };
        }

        private string GenerateStudentCode()
        {
            return $"STU-{Guid.NewGuid().ToString().Substring(0, 8).ToUpper()}";
        }

        private string GenerateEmployeeCode()
        {
            return $"EMP-{Guid.NewGuid().ToString().Substring(0, 8).ToUpper()}";
        }

        private string GenerateExternalCode()
        {
            return $"EXT-{Guid.NewGuid().ToString().Substring(0, 8).ToUpper()}";
        }
    }
}
