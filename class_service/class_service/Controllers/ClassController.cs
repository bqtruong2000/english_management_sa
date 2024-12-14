using class_service.ClassApi.Factories;
using class_service.ClassApi.Services;
using class_service.Models;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using static System.Runtime.InteropServices.JavaScript.JSType;

namespace class_service.Controllers
{
    [ApiController]
    [Route("[controller]")]
    public class ClassController : ControllerBase
    {
        private readonly ClassService _classService;
        private readonly IClassFactory _classFactory;

        public ClassController(ClassService classService, IClassFactory classFactory)
        {
            _classService = classService;
            _classFactory = classFactory;
        }


        // Get All Classes
        [HttpGet]
        public async Task<ActionResult<List<Class>>> GetProfiles()
        {
            var profiles = await _classService.GetAllProfilesAsync();
            return Ok(profiles);
        }


        // Get Class by ID
        [HttpGet("{id}")]
        public async Task<IActionResult> GetClassById(string id)
        {
            var classItem = await _classService.GetClassByIdAsync(id);
            if (classItem == null)
                return NotFound(new { Message = "Class not found" });

            return Ok(classItem);
        }

        // Filter Classes by Room
        [HttpGet("filter-by-room")]
        public async Task<IActionResult> FilterClassesByRoom([FromQuery] string room)
        {
            var classes = await _classService.GetClassesByRoomAsync(room);
            return Ok(classes);
        }

    }
}
