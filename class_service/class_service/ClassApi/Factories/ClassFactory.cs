using class_service.Models;

namespace class_service.ClassApi.Factories
{
    public interface IClassFactory
    {
        Class CreateClass(string type);
    }

    public class ClassFactory : IClassFactory
    {
        public Class CreateClass(string type)
        {
            return type.ToLower() switch
            {
                "lecture" => new Class
                {
                    ClassName = "New Lecture",
                    Room = "Default Room",
                    Duration = "1 Hour",
                    IdTeacher = GenerateTeacherId(),
                    ListStudent = new List<string>()
                },
                "lab" => new Class
                {
                    ClassName = "New Lab",
                    Room = "Lab Room",
                    Duration = "2 Hours",
                    IdTeacher = GenerateTeacherId(),
                    ListStudent = new List<string>()
                },
                _ => throw new ArgumentException("Invalid class type")
            };
        }

        private string GenerateTeacherId()
        {
            return $"TEACH-{Guid.NewGuid().ToString().Substring(0, 8).ToUpper()}";
        }
    }
}
